import Footer from '@/components/Footer';

import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {
  LoginForm,
  ProFormCheckbox,
  ProFormText,
} from '@ant-design/pro-components';
import { history, useModel } from '@umijs/max';
import { Alert, message, Tabs } from 'antd';
import React, { useState } from 'react';
// import { flushSync } from 'react-dom';
import styles from './index.less';
import {userLoginUsingPOST, userRegisterUsingPOST} from '@/services/backend/userController';

import {PLANET_LINK, SYSTEM_LOGO} from "@/constants";



const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};
const Login: React.FC = () => {
  const [userLoginState] = useState<API.LoginResult>({});
  const [type, setType] = useState<string>('account');
  const {  setInitialState } = useModel('@@initialState');

  const handleSubmitRegister = async (values: API.UserRegisterRequest) => {
    const {userPassword, checkPassword} = values;
    // 校验
    if (userPassword !== checkPassword) {
      message.error('两次输入的密码不一致');
      return;
    }

    try {
      // 注册
      const res = await userRegisterUsingPOST(values);
      if (res.data) {
        const defaultLoginFailureMessage = '注册成功！！！';
        message.success(defaultLoginFailureMessage);
        const urlParams = new URL(window.location.href).searchParams;
        history.push(urlParams.get('redirect') || '/');
        setInitialState({
          loginUser: res.data
        });
        return;
      }
    } catch (error: any) {
      const defaultLoginFailureMessage = '注册失败，请重试！';
      message.error(defaultLoginFailureMessage);
    }
  };

  const handleSubmit = async (values: API.UserLoginRequest) => {
    try {
      // 登录
      const res = await userLoginUsingPOST({
        ...values,
      });
      if (res.data) {
        const defaultLoginFailureMessage = '登录成功！！！';
        message.success(defaultLoginFailureMessage);
        const urlParams = new URL(window.location.href).searchParams;
        history.push(urlParams.get('redirect') || '/');
        setInitialState({
          loginUser: res.data
        });
        return;
      }
    } catch (error) {
      const defaultLoginFailureMessage = '登录失败，请重试！';
      console.log(error);
      message.error(defaultLoginFailureMessage);
    }
  };
  const { status, type: loginType } = userLoginState;
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <Tabs
          activeKey={type}
          onChange={setType}
          centered
          items={[
            {
              key: 'account',
              label: '登录',
            },
            {
              key: 'register',
              label: '注册',
            },
          ]}
        />

        {type === 'account'&&(<LoginForm
          logo={<img alt="logo" src={SYSTEM_LOGO} />}
          title="leafApi"
          subTitle={'leafApi'}
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.UserLoginRequest);
          }}
        >

          {status === 'error' && loginType === 'account' && (
            <LoginMessage content={'错误的用户名和密码'} />
          )}

            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'用户名: admin or user'}
                rules={[
                  {
                    required: true,
                    message: '用户名是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'密码: ant.design'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                ]}
              />
            </>

          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              自动登录
            </ProFormCheckbox>
            {/*<a*/}
            {/*  style={{*/}
            {/*    float: 'right',*/}
            {/*  }}*/}
            {/*>*/}
            {/*  忘记密码 ?*/}
            {/*</a>*/}
          </div>
        </LoginForm>)}
        {
          type==='register'&&(
            <LoginForm
              submitter={{
                searchConfig: {
                  submitText: '注册'
                }
              }}
              logo={<img alt="logo" src={SYSTEM_LOGO}/>}
              title="leafApi平台"
              subTitle={<a href={PLANET_LINK} target="_blank" rel="noreferrer">一个分享有趣API的平台</a>}
              initialValues={{
                autoLogin: true,
              }}
              onFinish={async (values) => {
                await handleSubmitRegister(values as API.UserRegisterRequest);
              }}
            >
              <Tabs activeKey={type} onChange={setType}>
                <Tabs.TabPane key="account" tab={'账号密码注册'}/>
              </Tabs>

                <>
                  <ProFormText
                    name="userAccount"
                    fieldProps={{
                      size: 'large',
                      prefix: <UserOutlined className={styles.prefixIcon}/>,
                    }}
                    placeholder="请输入账号"
                    rules={[
                      {
                        required: true,
                        message: '账号是必填项！',
                      },
                    ]}
                  />
                  <ProFormText.Password
                    name="userPassword"
                    fieldProps={{
                      size: 'large',
                      prefix: <LockOutlined className={styles.prefixIcon}/>,
                    }}
                    placeholder="请输入密码"
                    rules={[
                      {
                        required: true,
                        message: '密码是必填项！',
                      },
                      {
                        min: 8,
                        type: 'string',
                        message: '长度不能小于 8',
                      },
                    ]}
                  />
                  <ProFormText.Password
                    name="checkPassword"
                    fieldProps={{
                      size: 'large',
                      prefix: <LockOutlined className={styles.prefixIcon}/>,
                    }}
                    placeholder="请再次输入密码"
                    rules={[
                      {
                        required: true,
                        message: '确认密码是必填项！',
                      },
                      {
                        min: 8,
                        type: 'string',
                        message: '长度不能小于 8',
                      },
                    ]}
                  />

                </>

            </LoginForm>
          )
        }


      </div>
      <Footer />
    </div>
  );
};
export default Login;
