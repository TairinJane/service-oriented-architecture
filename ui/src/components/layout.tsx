import './layout.css';
import { Container } from '@mui/material';
import {
  FilterSorterContext,
  createFilterSorterContext,
} from './filter.context';
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
  const filterSorterContext = createFilterSorterContext();

  return (
    <div className="layout">
      <Container maxWidth="xl">
        <FilterSorterContext.Provider value={filterSorterContext}>
          <LocalizationProvider dateAdapter={DateAdapter}>
            <Header />
            <Sorter />
            <RoutesTable />
          </LocalizationProvider>
        </FilterSorterContext.Provider>
      </Container>
    </div>
  );
};
