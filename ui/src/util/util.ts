import { FilterSorter, RouteFields } from '../components/filter-sorter/config';
import { QueryFilter, QuerySort } from '../api/routes.api';
import { Route, RoutePartial } from '../store/routes.store';

export const capitalize = (str = '') =>
  str.length ? str[0].toUpperCase() + str.substring(1).toLowerCase() : str;

export const partialToRoute = (partial: RoutePartial): Route => {
  return partial as Route;
};

export const separateFilterSorter = (
  filterSorter: FilterSorter,
): { sort: QuerySort; filter: QueryFilter } => {
  const sort = [] as string[];
  const filter = {} as QueryFilter;
  Object.keys(filterSorter).forEach(key => {
    const routeKey = key as RouteFields;
    const sorting = filterSorter[routeKey].sorting;
    if (sorting) {
      if (sorting == 'desc') sort.push('-' + key);
      else sort.push(key);
    }
    const value = filterSorter[routeKey].value;
    if (value) {
      filter[routeKey] = value;
    }
  });

  return { sort, filter };
};
