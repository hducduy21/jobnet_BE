import { Global, Module } from '@nestjs/common';
import { ChatService } from './chat.service';
import { HttpModule } from '@nestjs/axios';

@Global()
@Module({
  imports: [HttpModule],
  providers: [ChatService],
  exports: [ChatService],
})
export class ChatModule {}
