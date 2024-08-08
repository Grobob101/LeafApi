import {Avatar, Button, Descriptions, Divider, message} from "antd";
import {history} from "@@/core/history";
import {AntDesignOutlined} from "@ant-design/icons";
import {SYSTEM_LOGO} from "@/constants";
import {
  getLoginUserUsingGET,
  getVerifyUsingGET,
  userLogoutUsingPOST
} from "@/services/backend/userController";

import {useEffect, useState} from "react";
const loginPath="/user/login";

const Person = () => {
  const [accessKey, setAccessKey] = useState('');
  const [sign, setSign] = useState('');
  const [userName, setUserName] = useState('');
  // const [totalNum, setTotalNum] = useState(0);
  // const [leftNum, setLeftNum] = useState(0);
  useEffect(() => {

    const fetchUserData = async () => {
      try {
        const res = await getVerifyUsingGET();
        const loginUser = await getLoginUserUsingGET();
        // const num=await getCountUsingGet();
        setAccessKey(res?.data?.accessKey||'无');
        setSign(res?.data?.sign||'无');
        setUserName(loginUser?.data?.userAccount||'无');
        // setTotalNum(loginUser.data.totalNum);
        // setLeftNum(loginUser.data.leftNum);
      } catch (error) {
        console.error('数据加载出错', error);
      }
    };
    fetchUserData();
  }, []);// 空数组表示只在组件挂载时执行一次


  const logout =async()=>{

    try{
      const res=await userLogoutUsingPOST()
      if(res.data){
        const defaultLoginFailureMessage = '登出成功！！！';
        message.success(defaultLoginFailureMessage);
        history.push(loginPath);
      }
    }catch(error: any){
      const defaultLoginFailureMessage = '登出失败！';
      message.error(defaultLoginFailureMessage);
    }

  }



return(

  <div>
   <Avatar  size={{ xs: 24, sm: 32, md: 40, lg: 64, xl: 80, xxl: 100 }}
            icon={<AntDesignOutlined />} src={SYSTEM_LOGO}/>

    <Divider />
    <Descriptions title="用户信息">
      <Descriptions.Item label="UserName">{userName}</Descriptions.Item>
      <Descriptions.Item label="accessKey">{accessKey}</Descriptions.Item>
      <Descriptions.Item label="sign">{sign}</Descriptions.Item>

    </Descriptions>
    <Divider />
    <Button type="primary" onClick={logout} >登出</Button>
  </div>

)

};

export default Person;
