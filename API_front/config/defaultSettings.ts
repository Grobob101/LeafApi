import { Settings as LayoutSettings } from '@ant-design/pro-components';


/**
 * @name
 */
const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',

  colorPrimary: '#138c23',
  layout: 'top',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: 'leaf-API',
  pwa: false,
  logo: "https://cdn.icon-icons.com/icons2/131/PNG/256/leaf_20579.png",
  iconfontUrl: '',
};

export default Settings;
