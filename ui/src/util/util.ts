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
      filter[routeKey] =
        routeKey == RouteFields.CREATION_DATE
          ? new Date(value).getTime()
          : value;
    }
  });

  return { sort, filter };
};

const hasContentType = (response: Response, type: 'json' | 'text'): boolean => {
  const contentType = response.headers.get('content-type');
  if (!contentType) return false;
  switch (type) {
    case 'json':
      return contentType?.includes('application/json');
    case 'text':
      return (
        contentType?.includes('text/html') ||
        contentType?.includes('text/plain')
      );
    default:
      return false;
  }
};

export const callApi = async (url: string, init?: RequestInit) => {
  const response = await fetch(url, init);
  if (response.ok) {
    const isJson = hasContentType(response, 'json');
    if (isJson) {
      return await response.json();
    } else {
      return await response.text();
    }
  } else {
    throw await response.text();
  }
};
