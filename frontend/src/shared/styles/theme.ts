const theme = {
  color: {
    primary: '#de2f5f',
    secondary: '#242424',
    success: '#52c41a',
    warning: '#fadb14',
    error: '#f5222d',
    link: '#1677ff',
    white: '#ffffff',
    black: '#000000',
    black500: '#0d0d0d',
    black400: '#1a1a1a',
    black300: '#17171C',
    black200: '#262626',
    disabledBackground: '#5d5d5d',
    disabled: '#2d2c2c',
    subText: '#a7a7a7',
  },

  breakPoints: {
    xxs: '380px',
    xs: '420px',
    sm: '640px',
    md: '768px',
    lg: '1024px',
    xl: '1280px',
    xxl: '1440px',
  },

  headerHeight: {
    mobile: '60px',
    tablet: '80px',
    desktop: '80px',
  },

  mainTopBottomPadding: {
    mobile: '16px',
    tablet: '36px',
    desktop: '36px',
  },
} as const;

export default theme;
