import { createGlobalStyle } from 'styled-components';
import PretendardRegular from '../../assets/fonts/Pretendard-Regular.subset.woff2';

const GlobalStyles = createGlobalStyle`
  @font-face {
    font-family: "Pretendard";
    font-weight: 400;
    font-style: normal;
    src:
      local('Pretendard'),
      url(${PretendardRegular}) format('woff2');
  }

  * {
    margin: 0;
    padding: 0;
    font: inherit;
    color: inherit;
    &::-webkit-scrollbar { 
      display: none;
      background: transparent;
      width: 0;
      height: 0;
    }
  }
  *,
  :after,
  :before {
    flex-shrink: 0;
    box-sizing: border-box;
  }
  :root {
    cursor: default;

    line-height: 1.5;
    -webkit-text-size-adjust: 100%;
    text-size-adjust: 100%;
    overflow-wrap: break-word;
    -moz-tab-size: 4;
    tab-size: 4;

    -webkit-tap-highlight-color: transparent;
  }
  img,  
  video, 
  svg {
    display: block;
    max-width: 100%;
  }
  button {
    cursor: pointer;
    background: none;
    border: 0;
    
    &:disabled {
      cursor: not-allowed;
    }
  }
  a {
    text-decoration: none;
    cursor: pointer;
  }
  table {
    border-spacing: 0;
    border-collapse: collapse;
  }

  ol, ul { 
    list-style: none;
  }

  body {
    font-family: 'Pretendard';
  }
`;

export default GlobalStyles;
