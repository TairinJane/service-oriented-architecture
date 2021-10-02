import { Route } from '../store/routes.store';
import { RouteFields } from '../components/filter-sorter/config';
import { stringify } from 'query-string';

const ROUTES_API_BASE = '/api/routes';

export type QuerySort = string[];
export type QueryFilter = Partial<Record<RouteFields, any>>;

type RoutesQuery = {
  sort?: QuerySort;
  limit?: number;
  offset?: number;
  filter?: QueryFilter;
};

const getRoutes = (query: RoutesQuery): Promise<Route[]> => {
  const queryString = stringify({
    sort: query.sort,
    limit: query.limit || 10,
    offset: query.offset || 0,
    ...query.filter,
  });
  console.log(queryString);
  return fetch(`${ROUTES_API_BASE}?${queryString}`).then(res => res.json());
};

const getRouteById = (id: number): Promise<Route> => {
  return fetch(`${ROUTES_API_BASE}/${id}`).then(res => res.json());
};

const addRoute = (route: Route): Promise<Route> => {
  return fetch(`${ROUTES_API_BASE}`, {
    method: 'POST',
    body: JSON.stringify(route),
  }).then(res => res.json());
};

const updateRoute = (route: Route): Promise<Route> => {
  return fetch(`${ROUTES_API_BASE}`, {
    method: 'PUT',
    body: JSON.stringify(route),
  }).then(res => res.json());
};

const deleteRouteById = (id: number): Promise<Route> => {
  return fetch(`${ROUTES_API_BASE}/${id}`, {
    method: 'DELETE',
  }).then(res => res.json());
};

const deleteWithDistance = (distance: number): Promise<Route> => {
  return fetch(`${ROUTES_API_BASE}/delete-with-distance?distance=${distance}`, {
    method: 'DELETE',
  }).then(res => res.json());
};

const countShorterThan = (distance: number): Promise<number> => {
  return fetch(
    `${ROUTES_API_BASE}/count-shorter-than?distance=${distance}`,
  ).then(res => res.json());
};

const getWithMinDistance = (): Promise<Route> => {
  return fetch(`${ROUTES_API_BASE}/min-distance`).then(res => res.json());
};

export const RoutesApi = {
  getRoutes,
  getRouteById,
  addRoute,
  updateRoute,
  deleteRouteById,
  deleteWithDistance,
  countShorterThan,
  getWithMinDistance,
};
