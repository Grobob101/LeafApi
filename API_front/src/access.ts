/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
import {Settings as ProSettings} from "@ant-design/pro-layout";


export default function access(initialState: {
  loginUser?: API.UserVO;
  settings?: ProSettings;
} | undefined) {
  const { loginUser } = initialState ?? {};
  return {
    canUser: loginUser,
    canAdmin: loginUser?.userRole === 'admin',
  };
}
