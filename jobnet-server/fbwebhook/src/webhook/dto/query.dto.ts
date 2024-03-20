import { IsNotEmpty } from 'class-validator';

export class QueryDto {
  @IsNotEmpty()
  'hub.mode': string;

  @IsNotEmpty()
  'hub.verify_token': string;

  @IsNotEmpty()
  'hub.challenge': string;
}
