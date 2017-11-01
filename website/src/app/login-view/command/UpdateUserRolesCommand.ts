import { Command } from '../../core/Command';
import { RoleJSON } from '../model/RoleJSON';

export class UpdateUserRolesCommand extends Command<RoleJSON[]> {

  constructor() {
    super('http://localhost:4201/gigs/rest/roles');
  }
}
