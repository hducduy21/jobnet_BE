import { Module } from '@nestjs/common';
import { WebhookModule } from './webhook/webhook.module';
import { ConfigModule } from '@nestjs/config';
import { FbModule } from './fb/fb.module';
import { ChatModule } from './chat/chat.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    WebhookModule,
    FbModule,
    ChatModule,
  ],
})
export class AppModule {}
