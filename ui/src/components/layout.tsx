import './layout.css';
import { Container, Grid } from '@mui/material';
import { Sorter } from './filter-sorter/sorter';
import { Table } from './table/table';
import React from 'react';

const Header = () => (
  <div>
    <h1>Lab #1 (1009)</h1>
  </div>
);

export const Layout: React.FC = () => {
  return (
    <div className="layout">
      <Container>
        <Header />
      </Container>
      <Grid container spacing={1}>
        <Grid item xs={3}>
          <Sorter />
        </Grid>
        <Grid item xs={9}>
          <Table />
        </Grid>
      </Grid>
    </div>
  );
};
