import { DatePicker, LocalizationProvider } from '@mui/lab';
import { FilterSorterField, RouteFields, SortType } from './config';
import { Grid, TextField } from '@mui/material';
import { SortingInput } from './sorting-input';
import DateAdapter from '@mui/lab/AdapterDateFns';
import React from 'react';

type DateFieldProps = {
  value: string;
  fieldName: RouteFields;
  setFieldValue: (field: RouteFields, value: string) => void;
  label: string;
};

const DateField: React.FC<DateFieldProps> = ({
  value,
  setFieldValue,
  fieldName,
  label,
}) => (
  <LocalizationProvider dateAdapter={DateAdapter}>
    <DatePicker
      value={value ? new Date(value) : null}
      onChange={value => setFieldValue(fieldName, value?.toISOString() || '')}
      renderInput={params => <TextField fullWidth size="small" {...params} />}
      label={label}
    />
  </LocalizationProvider>
);

type FilterFieldProps = {
  filterSorterField: FilterSorterField;
  fieldName: RouteFields;
  setFieldValue: (field: RouteFields, value: string) => void;
  setFieldSorting: (field: RouteFields, sorting: SortType) => void;
  type: 'text' | 'number' | 'date';
  title?: string;
  label: string;
};

export const FilterField: React.FC<FilterFieldProps> = ({
  filterSorterField,
  fieldName,
  setFieldValue,
  setFieldSorting,
  type,
  title,
  label,
}) => {
  return (
    <Grid item xs={12}>
      {title && <div className="section-label">{title}</div>}
      <Grid container spacing={1} alignItems="center">
        <Grid item xs>
          {type == 'date' ? (
            <DateField
              fieldName={fieldName}
              setFieldValue={setFieldValue}
              value={filterSorterField.value}
              label={label}
            />
          ) : (
            <TextField
              label={label}
              value={filterSorterField.value}
              onChange={e => setFieldValue(fieldName, e.target.value)}
              fullWidth
              type={type}
              size="small"
            />
          )}
        </Grid>
        <Grid item xs={4}>
          <SortingInput
            sorting={filterSorterField.sorting}
            field={fieldName}
            onChange={setFieldSorting}
          />
        </Grid>
      </Grid>
    </Grid>
  );
};
