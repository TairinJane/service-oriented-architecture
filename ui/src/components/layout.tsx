import './layout.css';
import { Container } from '@mui/material';
import { LocalizationProvider } from '@mui/lab';
import { RoutesTable } from './table/table';
import { Sorter } from './filter-sorter/sorter';
import DateAdapter from '@mui/lab/AdapterDateFns';
import React from 'react';

const Header = () => (
  <div>
    <h1>Lab #1 (1009)</h1>
  </div>
);

export const Layout: React.FC = () => {
  return (
    <div className="layout">
      <Container maxWidth="xl">
        <LocalizationProvider dateAdapter={DateAdapter}>
          <Header />
          <Sorter />
          <RoutesTable />
        </LocalizationProvider>
      </Container>
    </div>
  );
};
