import { Equals } from 'class-validator';

export class BodyDto {
  @Equals('page')
  object: string;
  entry: Array<Entry>;
}

export interface Entry {
  id: string;
  time: number;
  messaging: Array<Messaging>;
}

export interface Messaging {
  sender: { id: string };
  recipient: { id: string };
  timestamp: number;
  message: MessagingMessage;
}

export interface MessagingMessage {
  mid: string;
  text: string;
  nlp: object;
}
