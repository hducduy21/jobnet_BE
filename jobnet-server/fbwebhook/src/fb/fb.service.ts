import { Injectable } from '@nestjs/common';
import { Logger } from '@nestjs/common/services';
import { HttpService } from '@nestjs/axios';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class FbService {
  private readonly apiBaseUrl = 'https://graph.facebook.com/v18.0';
  private readonly logger = new Logger(FbService.name);

  constructor(
    private readonly configService: ConfigService,
    private readonly httpService: HttpService,
  ) {}

  async getConversationId(pageId: string, senderPsid: string) {
    return this.httpService.axiosRef
      .get(`${this.apiBaseUrl}/${pageId}/conversations`, {
        params: {
          user_id: senderPsid,
          access_token: this.configService.get('PAGE_ACCESS_TOKEN'),
        },
      })
      .then((res) => {
        this.logger.log('Get conversation ID');
        return res.data.data[0].id as string;
      })
      .catch((err) => {
        this.logger.error(err.message);
        return undefined as string;
      });
  }

  async getHistoryMessages(conversationId: string) {
    return this.httpService.axiosRef
      .get(`${this.apiBaseUrl}/${conversationId}/messages`, {
        params: {
          fields: 'id,message,from',
          access_token: this.configService.get('PAGE_ACCESS_TOKEN'),
        },
      })
      .then((res) => {
        this.logger.log('Get history messages');
        return res.data.data as Array<any>;
      })
      .catch((err) => {
        this.logger.error(err.message);
        return [];
      });
  }

  callSendAPI(senderPsid: string, response: object) {
    const requestBody = {
      recipient: {
        id: senderPsid,
      },
      message: response,
    };

    this.httpService.axiosRef
      .post(`${this.apiBaseUrl}/me/messages`, requestBody, {
        params: { access_token: this.configService.get('PAGE_ACCESS_TOKEN') },
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(() => this.logger.log('Message sent!'))
      .catch((err) => this.logger.error('Unable to send message:' + err));
  }
}
