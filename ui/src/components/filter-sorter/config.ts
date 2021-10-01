import { capitalize } from '../../util/util';

export type SortType = 'asc' | 'desc' | '';

type Sorting = Record<RouteFields, { sorting: SortType; value: string }>;

export enum RouteFields {
  ID = 'id',
  NAME = 'name',
  COORDINATES_X = 'coordinates.x',
  COORDINATES_Y = 'coordinates.y',
  CREATION_DATE = 'creationDate',
  FROM_X = 'from.x',
  FROM_Y = 'from.y',
  FROM_NAME = 'from.name',
  TO_X = 'to.x',
  TO_Y = 'to.y',
  TO_Z = 'to.z',
  DISTANCE = 'distance',
}

export const initialSorting: Sorting = Object.values(RouteFields).reduce(
  (sorting, field) => {
    sorting[field] = {
      sorting: '',
      value: '',
    };
    return sorting;
  },
  {} as Sorting,
);

export const getFieldLabel = (routeField: RouteFields) =>
  capitalize(routeField.split('.')[0]);

export const getFieldEndLabel = (routeField: RouteFields) =>
  capitalize(routeField.split('.')[1]);

export type Field = {
  label?: string;
  fieldName: string;
  value: any;
  sorting: SortType;
};
type FieldContainer = { label: string; fields: Field[] };
type FilterSorter = FieldContainer[];

export const filterSorterConfig: FilterSorter = [
  { label: 'Id', fields: [{ fieldName: 'id', value: null, sorting: '' }] },
  {
    label: 'Name',
    fields: [{ fieldName: 'name', value: null, sorting: '' }],
  },
  {
    label: 'Coordinates',
    fields: [
      { label: 'X', fieldName: 'coordinates.x', value: null, sorting: '' },
      { label: 'Y', fieldName: 'coordinates.y', value: null, sorting: '' },
    ],
  },
  {
    label: 'From',
    fields: [
      { label: 'X', fieldName: 'from.x', value: null, sorting: '' },
      { label: 'Y', fieldName: 'from.y', value: null, sorting: '' },
      { label: 'Name', fieldName: 'from.name', value: null, sorting: '' },
    ],
  },
  {
    label: 'To',
    fields: [
      { label: 'X', fieldName: 'to.x', value: null, sorting: '' },
      { label: 'Y', fieldName: 'to.y', value: null, sorting: '' },
      { label: 'Z', fieldName: 'to.z', value: null, sorting: '' },
    ],
  },
  {
    label: 'Distance',
    fields: [{ fieldName: 'distance', value: null, sorting: '' }],
  },
];

export const filterSorterInitialState = filterSorterConfig
  .map(field => field.fields)
  .flat(2);
