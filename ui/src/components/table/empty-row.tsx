import { TableCell, TableRow } from '@mui/material';
import React from 'react';

type EmptyRowProps = {
  rowSpan: number;
  colSpan: number;
  text?: string;
};

export const EmptyRow: React.FC<EmptyRowProps> = ({
  rowSpan,
  colSpan,
  text,
}) => {
  return (
    <TableRow
      style={{
        height: 43 * rowSpan,
      }}
    >
      <TableCell colSpan={colSpan}>{text}</TableCell>
    </TableRow>
  );
};
