import { Global, Module } from '@nestjs/common';
import { FbService } from './fb.service';
import { HttpModule } from '@nestjs/axios';

@Global()
@Module({
  imports: [HttpModule],
  providers: [FbService],
  exports: [FbService],
})
export class FbModule {}
