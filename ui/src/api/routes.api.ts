import { Route } from '../store/routes.store';
import { stringify } from 'query-string';

const ROUTES_API_BASE = '/api/routes';

type RoutesQuery = {
  sort?: string;
  limit?: number;
  offset?: number;
  filter?: Partial<Route>;
};

const getRoutes = (query: RoutesQuery): Promise<Route[]> => {
  const queryString = stringify({
    sort: query.sort,
    limit: query.limit || 10,
    offset: query.offset || 0,
    ...query.filter,
  });
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

const deleteRouteById = (id: number): Promise<number> => {
  return fetch(`${ROUTES_API_BASE}/${id}`, {
    method: 'DELETE',
  }).then(res => id);
};

const deleteWithDistance = (distance: number): Promise<boolean> => {
  return fetch(`${ROUTES_API_BASE}/delete-with-distance?distance=${distance}`, {
    method: 'DELETE',
  }).then(res => res.ok);
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
