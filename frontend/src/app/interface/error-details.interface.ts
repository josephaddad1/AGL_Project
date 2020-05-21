import { ServiceResponseBase } from './service-response-base-interface';

export class ErrorDetails extends ServiceResponseBase<string> {
  timestamp: Date;
  status: number;
  error: string;
}
