import { RouteFields, SortType, initialFilterSorter } from './config';
import { RoutesTable } from '../table/table';
import { RoutesThunks } from '../../thunks/routes.thunks';
import { Sorter } from './sorter';
import { separateFilterSorter } from '../../util/util';
import { useDispatch, useSelector } from '../../store/hooks';
import React, { useEffect, useState } from 'react';

export const FilterSorterContext: React.FC = () => {
  const [filterSorter, setFilterSorter] = useState(initialFilterSorter);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const dispatch = useDispatch();
  const { entities: routes, hasMore } = useSelector(state => state.routes);

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

  const fetchRoutesWithFilter = (offset: number, limit: number) => {
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
    fetchRoutesWithFilter(0, rowsPerPage);
  };

  useEffect(() => {
    if (routes.length) return;
    dispatch(RoutesThunks.getRoutes({ offset: 0, limit: rowsPerPage }));
  }, []);

  const onPageChange = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number,
  ) => {
    setPage(newPage);
    const pageStart = newPage * rowsPerPage;
    const pageEnd = pageStart + rowsPerPage;
    if (routes.length < pageEnd && hasMore) {
      const offset = Math.max(routes.length, pageStart);
      const limit = Math.min(rowsPerPage, pageEnd - routes.length);
      fetchRoutesWithFilter(offset, limit);
    }
  };

  const onRowsPerPageChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    const newRowsPerPage = parseInt(event.target.value, 10);
    setRowsPerPage(newRowsPerPage);
    setPage(0);
    if (routes.length < newRowsPerPage && hasMore) {
      const offset = routes.length;
      const limit = newRowsPerPage - routes.length;
      fetchRoutesWithFilter(offset, limit);
    }
  };

  const clearFilter = () => {
    setFilterSorter(initialFilterSorter);
  };

  return (
    <>
      <Sorter
        filterSorter={filterSorter}
        setFieldSorting={setFieldSorting}
        setFieldValue={setFieldValue}
        getFilteredRoutes={getFilteredRoutes}
        clearFilter={clearFilter}
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
