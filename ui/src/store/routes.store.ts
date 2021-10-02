export type Coordinates = {
  id?: number;
  x: number;
  y: number;
};

export type LocationFrom = {
  id?: number;
  x: number;
  y: number;
  name?: string;
};

export type LocationTo = {
  id?: number;
  x: number;
  y: number;
  z: number;
};

export type Route = {
  id?: number;
  name: string;
  coordinates: Coordinates;
  creationDate: Date;
  from?: LocationFrom;
  to: LocationTo;
  distance: number;
};

export type RoutePartial = {
  id?: number;
  name: string;
  coordinates: Partial<Coordinates>;
  from?: Partial<LocationFrom>;
  to: Partial<LocationTo>;
  creationDate: Date | null;
  distance?: number;
};

export const emptyRoute: RoutePartial = {
  name: '',
  coordinates: {},
  to: {},
  creationDate: null,
  distance: undefined,
};
