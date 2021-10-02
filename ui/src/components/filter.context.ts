import { Sorting, initialFilterSorter } from './filter-sorter/config';
import React, { useState } from 'react';

type FilterSorterContext = {
  filterSorter: Sorting;
  setFilterSorter: (filterSorter: Sorting) => void;
  page: number;
  setPage: (page: number) => void;
  rowsPerPage: number;
  setRowsPerPage: (rowsPerPage: number) => void;
};

const defaultFilterSorterContext: FilterSorterContext = {
  filterSorter: initialFilterSorter,
  setFilterSorter: () => null,
  page: 0,
  setPage: () => null,
  rowsPerPage: 5,
  setRowsPerPage: () => null,
};

export const createFilterSorterContext = (): FilterSorterContext => {
  const [filterSorter, setFilterSorter] = useState(initialFilterSorter);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  return {
    filterSorter,
    setFilterSorter,
    page,
    setPage,
    rowsPerPage,
    setRowsPerPage,
  };
};

export const FilterSorterContext = React.createContext(
  defaultFilterSorterContext,
);
