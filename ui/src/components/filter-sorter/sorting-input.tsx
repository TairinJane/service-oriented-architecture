import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { RouteFields, SortType } from './config';
import React from 'react';

type SortingInputProps = {
  field: RouteFields;
  sorting: SortType;
  onChange: (field: RouteFields, sorting: SortType) => void;
};

export const SortingInput: React.FC<SortingInputProps> = ({
  field,
  onChange,
  sorting,
}) => {
  return (
    <FormControl fullWidth size="small">
      <InputLabel id={`${field}-select-label`}>Sorting</InputLabel>
      <Select
        id={`${field}-select`}
        value={sorting}
        onChange={e => onChange(field, e.target.value as SortType)}
        labelId={`${field}-select-label`}
        label="Sorting"
      >
        <MenuItem value="asc">Asc</MenuItem>
        <MenuItem value="desc">Desc</MenuItem>
        <MenuItem value={''}>No</MenuItem>
      </Select>
    </FormControl>
  );
};
