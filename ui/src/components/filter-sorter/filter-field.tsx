import { Box, Stack, TextField } from '@mui/material';
import { DatePicker } from '@mui/lab';
import { FilterSorterField, RouteFields, SortType } from './config';
import { SortingInput } from './sorting-input';
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
  <DatePicker
    value={value ? new Date(value) : null}
    onChange={value => setFieldValue(fieldName, value?.toISOString() || '')}
    renderInput={params => <TextField fullWidth size="small" {...params} />}
    label={label}
  />
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
    <Box>
      {title && <div className="section-label">{title}</div>}
      <Stack spacing={1} alignItems="center">
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
        <SortingInput
          sorting={filterSorterField.sorting}
          field={fieldName}
          onChange={setFieldSorting}
        />
      </Stack>
    </Box>
  );
};
