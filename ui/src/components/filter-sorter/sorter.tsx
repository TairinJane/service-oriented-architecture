import './sorter.css';
import { Alert, Grid, Paper } from '@mui/material';
import { FilterField } from './filter-field';
import {
  RouteFields,
  SortType,
  getFieldEndLabel,
  getFieldLabel,
  initialFilterSorter,
} from './config';
import React, { useState } from 'react';

export const Sorter: React.FC = () => {
  const [filterSorter, setFilterSorter] = useState(initialFilterSorter);

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

  return (
    <Paper variant="outlined" className="sorter">
      <Grid container spacing={1} direction="column">
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
          fieldName={RouteFields.CREATION_DATE}
          setFieldValue={setFieldValue}
          filterSorterField={filterSorter[RouteFields.CREATION_DATE]}
          label={RouteFields.CREATION_DATE}
          setFieldSorting={setFieldSorting}
          type="date"
          title={RouteFields.CREATION_DATE}
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
      </Grid>
      <Alert severity="info" style={{ marginTop: 8 }}>
        <code>sorting: {JSON.stringify(filterSorter)}</code>
      </Alert>
    </Paper>
  );
};
