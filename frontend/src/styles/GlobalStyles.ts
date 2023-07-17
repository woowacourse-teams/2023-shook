import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
  * {  
    box-sizing: border-box;
    padding: 0;
    margin: 0;
  }

  li, ul {
    list-style: none;
  }

  a {
    text-decoration: none;
  }

  input{
    border: none;
    box-shadow: none;
    -webkit-box-shadow: none;
    background-color: transparent;
  }
`;

export default GlobalStyles;
