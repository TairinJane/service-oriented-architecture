import { RouteFields, SortType, initialFilterSorter } from './config';
import { RoutesTable } from '../table/table';
import { RoutesThunks } from '../../thunks/routes.thunks';
import { Sorter } from './sorter';
import { separateFilterSorter } from '../../util/util';
import { useDispatch, useSelector } from '../../store/hooks';
import React, { useState } from 'react';

export const FilterSorterContext: React.FC = () => {
  const [filterSorter, setFilterSorter] = useState(initialFilterSorter);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const dispatch = useDispatch();
  const routes = useSelector(state => state.routes.entities);

  const setFieldSorting = (field: RouteFields, sorting: SortType) => {
    setFilterSorter({
      ...filterSorter,
      [field]: { ...filterSorter[field], sorting },
    });
  };

  const setFieldValue = (field: RouteFields, value: string) => {
    setFilterSorter({
      ...filterSorter,
      [field]: { ...filterSorter[field], value },
    });
  };

  const fetchFiltered = (offset: number, limit: number) => {
    const { sort, filter } = separateFilterSorter(filterSorter);
    dispatch(
      RoutesThunks.getRoutes({
        sort,
        filter,
        offset,
        limit,
      }),
    );
  };

  const getFilteredRoutes = () => {
    setPage(0);
    fetchFiltered(0, rowsPerPage);
  };

  const onPageChange = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number,
  ) => {
    setPage(newPage);
    if (routes.length < newPage * rowsPerPage) {
      fetchFiltered(newPage * rowsPerPage, rowsPerPage);
    }
  };

  const onRowsPerPageChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <>
      <Sorter
        filterSorter={filterSorter}
        setFieldSorting={setFieldSorting}
        setFieldValue={setFieldValue}
        getFilteredRoutes={getFilteredRoutes}
      />
      <RoutesTable
        page={page}
        onPageChange={onPageChange}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={onRowsPerPageChange}
      />
    </>
  );
};
