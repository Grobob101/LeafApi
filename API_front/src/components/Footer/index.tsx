import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import '@umijs/max';
const Footer: React.FC = () => {
  const defaultMessage = '手拿键盘一路火花出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'leaf-api',
          title: 'leaf-api',
          href: 'https://blog.csdn.net/bobplease?type=blog',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/Grobob101',
          blankTarget: true,
        },
        {
          key: 'leaf-api',
          title: 'leaf-api',
          href: 'https://blog.csdn.net/bobplease?type=blog',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
