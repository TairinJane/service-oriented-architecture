import './sorter.css';
import { Button, Paper, Stack } from '@mui/material';
import { FilterField } from './filter-field';
import {
  FilterSorter,
  RouteFields,
  SortType,
  getFieldEndLabel,
  getFieldLabel,
} from './config';
import React from 'react';

type SorterProps = {
  filterSorter: FilterSorter;
  setFieldSorting: (field: RouteFields, sorting: SortType) => void;
  setFieldValue: (field: RouteFields, value: string) => void;
  getFilteredRoutes: () => void;
  clearFilter: () => void;
};

export const Sorter: React.FC<SorterProps> = ({
  filterSorter,
  setFieldSorting,
  setFieldValue,
  getFilteredRoutes,
  clearFilter,
}) => {
  return (
    <Paper variant="outlined" className="sorter">
      <Stack spacing={1}>
        <Stack spacing={1} direction="row" alignItems="flex-end">
          <FilterField
            fieldName={RouteFields.ID}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.ID]}
            label={getFieldLabel(RouteFields.ID)}
            setFieldSorting={setFieldSorting}
            type="number"
            title={getFieldLabel(RouteFields.ID)}
          />
          <FilterField
            fieldName={RouteFields.NAME}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.NAME]}
            label={getFieldLabel(RouteFields.NAME)}
            setFieldSorting={setFieldSorting}
            type="text"
            title={getFieldLabel(RouteFields.NAME)}
          />
          <FilterField
            fieldName={RouteFields.COORDINATES_X}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.COORDINATES_X]}
            label={getFieldEndLabel(RouteFields.COORDINATES_X)}
            setFieldSorting={setFieldSorting}
            type="number"
            title={getFieldLabel(RouteFields.COORDINATES_X)}
          />
          <FilterField
            fieldName={RouteFields.COORDINATES_Y}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.COORDINATES_Y]}
            label={getFieldEndLabel(RouteFields.COORDINATES_Y)}
            setFieldSorting={setFieldSorting}
            type="number"
          />
          <FilterField
            fieldName={RouteFields.FROM_X}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.FROM_X]}
            label={getFieldEndLabel(RouteFields.FROM_X)}
            setFieldSorting={setFieldSorting}
            type="number"
            title={getFieldLabel(RouteFields.FROM_X)}
          />
          <FilterField
            fieldName={RouteFields.FROM_Y}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.FROM_Y]}
            label={getFieldEndLabel(RouteFields.FROM_Y)}
            setFieldSorting={setFieldSorting}
            type="number"
          />
          <FilterField
            fieldName={RouteFields.FROM_NAME}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.FROM_NAME]}
            label={getFieldEndLabel(RouteFields.FROM_NAME)}
            setFieldSorting={setFieldSorting}
            type="text"
          />
          <FilterField
            fieldName={RouteFields.TO_X}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.TO_X]}
            label={getFieldEndLabel(RouteFields.TO_X)}
            setFieldSorting={setFieldSorting}
            type="number"
            title={getFieldLabel(RouteFields.TO_X)}
          />
          <FilterField
            fieldName={RouteFields.TO_Y}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.TO_Y]}
            label={getFieldEndLabel(RouteFields.TO_Y)}
            setFieldSorting={setFieldSorting}
            type="number"
          />
          <FilterField
            fieldName={RouteFields.TO_Z}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.TO_Z]}
            label={getFieldEndLabel(RouteFields.TO_Z)}
            setFieldSorting={setFieldSorting}
            type="number"
          />
          <FilterField
            fieldName={RouteFields.DISTANCE}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.DISTANCE]}
            label={getFieldLabel(RouteFields.DISTANCE)}
            setFieldSorting={setFieldSorting}
            type="number"
            title={getFieldLabel(RouteFields.DISTANCE)}
          />
          <FilterField
            fieldName={RouteFields.CREATION_DATE}
            setFieldValue={setFieldValue}
            filterSorterField={filterSorter[RouteFields.CREATION_DATE]}
            label={RouteFields.CREATION_DATE}
            setFieldSorting={setFieldSorting}
            type="date"
            title={RouteFields.CREATION_DATE}
          />
        </Stack>
        <Button variant="contained" fullWidth onClick={getFilteredRoutes}>
          Apply
        </Button>
        <Button variant="outlined" fullWidth onClick={clearFilter}>
          Clear Filter
        </Button>
      </Stack>
    </Paper>
  );
};
