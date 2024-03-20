import { ForbiddenException, Injectable, Logger } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { BodyDto, Entry, MessagingMessage, QueryDto } from './dto';
import { FbService } from 'src/fb/fb.service';
import { ChatService } from 'src/chat/chat.service';

@Injectable()
export class WebhookService {
  private readonly logger = new Logger(WebhookService.name);

  constructor(
    private readonly configService: ConfigService,
    private readonly fbService: FbService,
    private readonly chatService: ChatService,
  ) {}

  validateVerificationRequest(query: QueryDto) {
    const mode = query['hub.mode'];
    const token = query['hub.verify_token'];
    const challenge = query['hub.challenge'];

    if (
      mode !== 'subscribe' ||
      token !== this.configService.get('VERIFY_TOKEN')
    ) {
      this.logger.error('Forbidden');
      throw new ForbiddenException();
    }
    this.logger.log('WEBHOOK_VERIFIED');
    return challenge;
  }

  async receiveWebhookNotification(body: BodyDto) {
    const handleEntry = async (entry: Entry) => {
      const webhookEvent = entry.messaging[0];
      const senderPsid = webhookEvent.sender.id;
      const pageId = webhookEvent.recipient.id;

      if (webhookEvent.message) {
        this.handleMessage(senderPsid, pageId, webhookEvent.message);
      }
    };

    this.logger.log('EVENT_RECEIVED');

    // Iterates over each entry - there may be multiple if batched
    body.entry.forEach(handleEntry);
    return 'EVENT_RECEIVED';
  }

  private async handleMessage(
    senderPsid: string,
    pageId: string,
    receivedMessage: MessagingMessage,
  ) {
    const conversationId = await this.fbService.getConversationId(
      pageId,
      senderPsid,
    );
    const historyMessages = (
      await this.fbService.getHistoryMessages(conversationId)
    )
      .map((item) => ({
        role: item.from.id === senderPsid ? 'user' : 'assistant',
        content: item.message,
      }))
      .reverse();

    let response;
    if (receivedMessage.text) {
      historyMessages.push({
        role: 'user',
        content: receivedMessage.text,
      });
      const message = await this.chatService.getMessage(historyMessages);
      response = { text: message.content };
    }

    this.fbService.callSendAPI(senderPsid, response);
  }
}
