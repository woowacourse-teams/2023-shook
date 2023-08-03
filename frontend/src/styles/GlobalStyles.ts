import { createGlobalStyle } from 'styled-components';
import PretendardRegular from '@/assets/fonts/Pretendard-Regular.subset.woff2';

const GlobalStyles = createGlobalStyle`
  @font-face {
    font-family: "Pretendard";
    src: local('Pretendard') url(${PretendardRegular}) format('woff2');

    font-weight: 400;
    font-style: normal;
  }

  * {
    margin: 0;
    padding: 0;
    font: inherit;
    color: inherit;
  }
  *,
  :after,
  :before {
    box-sizing: border-box;
    flex-shrink: 0;
  }
  :root {
    -webkit-tap-highlight-color: transparent;
    -webkit-text-size-adjust: 100%;
    text-size-adjust: 100%;
    cursor: default;
    line-height: 1.5;
    overflow-wrap: break-word;
    -moz-tab-size: 4;
    tab-size: 4;
  }
  img,  
  video, 
  svg {
    display: block;
    max-width: 100%;
  }
  button {
    background: none;
    border: 0;
    cursor: pointer;
  }
  a {
    text-decoration: none;
  }
  table {
    border-collapse: collapse;
    border-spacing: 0;
  }

  ol, ul { 
    list-style: none;
  }

  body {
    font-family: 'Pretendard';
  }
`;

export default GlobalStyles;
