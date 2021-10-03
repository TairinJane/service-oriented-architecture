import { Route } from '../../store/routes.store';
import React from 'react';

export const PrettyRoute: React.FC<{ route: Route }> = ({ route }) => {
  return (
    <div>
      <div>ID: {route.id}</div>
      <div>Name: {route.name}</div>
      <div>
        Coordinates: {`(${route.coordinates.x};${route.coordinates.y})`}
      </div>
      <div>
        From:{' '}
        {route.from
          ? `${route.from?.name}(${route.from?.x};${route.from?.y})`
          : 'Empty'}
      </div>
      <div>To: {`(${route.to.x};${route.to.y};${route.to.z})`}</div>
      <div>Distance: {route.distance}</div>
      <div>
        Creation Date: {new Date(route.creationDate).toLocaleDateString('ru')}
      </div>
    </div>
  );
};
