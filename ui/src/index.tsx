import './index.css';
import { Layout } from './components/layout';
import { Provider } from 'react-redux';
import { SnackbarProvider } from 'notistack';
import { store } from './store/store';
import React from 'react';
import ReactDOM from 'react-dom';

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <SnackbarProvider maxSnack={3}>
        <Layout />
      </SnackbarProvider>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root'),
);
