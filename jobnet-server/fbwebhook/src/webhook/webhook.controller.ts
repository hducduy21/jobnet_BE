import { Controller, Get, Post, Query, Body } from '@nestjs/common';
import { WebhookService } from './webhook.service';
import { QueryDto } from './dto';
import { BodyDto } from './dto/body.dto';

@Controller('webhook')
export class WebhookController {
  constructor(private readonly webhookService: WebhookService) {}

  @Get()
  validateVerificationRequest(@Query() query: QueryDto) {
    return this.webhookService.validateVerificationRequest(query);
  }

  @Post()
  receiveWebhookNotification(@Body() body: BodyDto) {
    return this.webhookService.receiveWebhookNotification(body);
  }
}
