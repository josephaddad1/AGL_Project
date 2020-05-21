import { BehaviorSubject } from 'rxjs';
import { User } from 'src/app/interface/user.interface';

export class Constants {




  // using this behaviorsubject for session and logout and to diable the nav bar and side bar in AppComponent
  static user: BehaviorSubject<User> = new BehaviorSubject(null);
  static isAdmin: BehaviorSubject<boolean> = new BehaviorSubject(null);
  static userData: User;


  // URLs
  // my-pc IP for serving on network
  static readonly CBK_BASE_URL = 'http://10.1.116.111:8034';
  // static readonly CBK_BASE_URL = 'http://localhost:8034';
  static readonly CBK_AUTHENTICATION_URL: string = Constants.CBK_BASE_URL + '/authentication';
  static readonly CBK_AUTH_URL: string = Constants.CBK_AUTHENTICATION_URL + '/authenticate';
  static readonly CBK_NEW_TOKEN_URL: string = Constants.CBK_AUTHENTICATION_URL + '/newToken';
  static readonly CBK_RESET_PASSWORD_URL = Constants.CBK_AUTHENTICATION_URL + '/resetPassword';
  static readonly CBK_CHAMGE_PASSWORD_URL = Constants.CBK_AUTHENTICATION_URL + '/changePassword';
  static readonly CBK_REQUEST_RESET_PASSWORD_URL = Constants.CBK_AUTHENTICATION_URL + '/requestResetPassword';

  static readonly CBK_APPLURL: string = Constants.CBK_BASE_URL + '/appls';
  static readonly CBK_USER_URL: string = Constants.CBK_BASE_URL + '/user';
  static readonly CBK_MODULE_URL: string = Constants.CBK_BASE_URL + '/modules';
  static readonly CBK_DELIVERY_URL: string = Constants.CBK_BASE_URL + '/deliveries';

  // deliveries
  static readonly CBK_DELETE_DELIVERY_URL: string = Constants.CBK_DELIVERY_URL + '/delete';

  //appl 

  static readonly CBK_APPLS_BY_NAME_URL: string = Constants.CBK_APPLURL + '/appl-by-name';
  static readonly CBK_APPLS_BY_NAME_In_URL: string = Constants.CBK_APPLURL + '/appl-in';
  static readonly CBK_APPL_CONTENT_URL: string = Constants.CBK_APPLURL + '/appl-content';
  static readonly CBK_APPL_CONTENT_BY_PARENT_ID_URL: string = Constants.CBK_APPLURL + '/appl-content-by-parent-id';
  static readonly CBK_APPLURL_UPDATE  : string = Constants.CBK_APPLURL +'/update-appl'


  // users
  static readonly CBK_GET_USER_URL: string = Constants.CBK_USER_URL + '/allusers';
  static readonly CBK_INSERT_USER_URL: string = Constants.CBK_USER_URL + '/insert';
  static readonly CBK_GET_USER_ROLES_URL: string = Constants.CBK_USER_URL + '/roles';
  static readonly CBK_USERS_LIST_URL: string = Constants.CBK_USER_URL + '/getUsersList';

  // modules
  static readonly CBK_MODULES_LIST_URL: string = Constants.CBK_MODULE_URL + '/modules-list';
  static readonly CBK_ADD_MODULE_URL: string = Constants.CBK_MODULE_URL + '/add-module';
  static readonly CBK_MODULES_VIEW_LIST_URL: string = Constants.CBK_MODULE_URL + '/modules-view-list';
  static readonly CBK_MODULES_DELETE_APP_URL: string = Constants.CBK_MODULE_URL + '/delete-application';
  static readonly CBK_SUBMODULES_LIST_URL: string = Constants.CBK_MODULE_URL + '/sub-modules-list';
  static readonly CBK_ADD_SUBMODULE_URL: string = Constants.CBK_MODULE_URL + '/add-subModule';
  static readonly CBK_SUBMODULESAPPS_LIST_URL: string = Constants.CBK_MODULE_URL + '/subModuleApps-list';
  static readonly CBK_ADD_SUBMODULESAPP_URL: string = Constants.CBK_MODULE_URL + '/add-subModuleApp';
















  static readonly CBK_USER_ROLE_URL: string = Constants.CBK_BASE_URL + '/user/role';
  static readonly CBK_USER_ROLE_CODE_URL: string = Constants.CBK_BASE_URL + '/user/role-code';


  // Errors
  static readonly CBK_AUTH_001 = 'Incorrect username or password';
  static readonly CBK_AUTH_002 = 'User disabled';
  static readonly CBK_AUTH_003 = 'Username not found';
  static readonly CBK_AUTH_004 = 'Session Expired';
  static readonly CBK_AUTH_005 = 'JWT Token does not begin with Bearer String';
  static readonly CBK_AUTH_006 = 'Unable to get JWT Token';
  static readonly CBK_AUTH_007 = 'Invalid token';
  static readonly CBK_AUTH_008 = 'MalFormed JWT Token';
  static readonly CBK_UKE_01 = 'Unkown error';

  // session time in secondes
  static readonly SESSION_TIME = 60 * 60;



}

