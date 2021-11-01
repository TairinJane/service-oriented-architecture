import './layout.css';
import { AdditionalActions } from './additional-actions/additional-actions';
import { Container, Stack } from '@mui/material';
import { FilterSorterContext } from './filter-sorter/filter-sorter-context';
import { LocalizationProvider } from '@mui/lab';
import DateAdapter from '@mui/lab/AdapterDateFns';
import React from 'react';

const Header = () => (
  <div>
    <h1>Lab #2 (1009.12)</h1>
  </div>
);

export const Layout: React.FC = () => {
  return (
    <div className="layout">
      <Container maxWidth="xl">
        <LocalizationProvider dateAdapter={DateAdapter}>
          <Stack spacing={1}>
            <Header />
            <AdditionalActions />
            <FilterSorterContext />
          </Stack>
        </LocalizationProvider>
      </Container>
    </div>
  );
};
