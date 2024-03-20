import { HttpService } from '@nestjs/axios';
import { Injectable, Logger } from '@nestjs/common';
import { Message } from 'src/webhook/dto';

@Injectable()
export class ChatService {
  private readonly logger = new Logger(ChatService.name);

  constructor(private readonly httpService: HttpService) {}

  async getMessage(messages: Array<any>) {
    return this.httpService.axiosRef
      .post(
        'http://127.0.0.1:8000/api/chat/',
        { messages },
        { headers: { 'Content-Type': 'application/json' } },
      )
      .then((res) => {
        if (res.status !== 201)
          throw new Error('Unexpected response status from chat service');

        this.logger.log('Generate text from chat service');
        return res.data as Message;
      })
      .catch((err) => {
        this.logger.error(err.message);
        return undefined as Message;
      });
  }
}
