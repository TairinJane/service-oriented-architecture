import './layout.css';
import { Container, Grid } from '@mui/material';
import { RoutesTable } from './table/table';
import { Sorter } from './filter-sorter/sorter';
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
        <Header />
        <Grid container spacing={3}>
          <Grid item xs={3}>
            <Sorter />
          </Grid>
          <Grid item xs={9}>
            <RoutesTable />
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};
