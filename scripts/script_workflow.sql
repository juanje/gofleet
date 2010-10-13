while true
do
  psql -U demogis -c "update incidencias set fk_estado = 1 where x_incidencia < 4;";
  psql -U demogis -c "update incidencias set prioridad = prioridad + 1 where prioridad < 7";
  psql -U demogis -c "update incidencias set prioridad = prioridad - 3 where prioridad > 6";
  psql -U demogis -c "update incidencias set fk_estado = 2 where x_incidencia < 8 and x_incidencia >= 4;";
  psql -U demogis -c "update incidencias set fk_estado = 3 where x_incidencia >= 8;";
  sleep 5;
  psql -U demogis -c "update incidencias set fk_estado = 2 where x_incidencia < 4;";
  psql -U demogis -c "update incidencias set fk_estado = 3 where x_incidencia < 8 and x_incidencia >= 4;";
  psql -U demogis -c "update incidencias set prioridad = prioridad + 1 where prioridad < 6";
  psql -U demogis -c "update incidencias set prioridad = prioridad - 2 where prioridad > 5";
  psql -U demogis -c "update incidencias set fk_estado = 1 where x_incidencia >= 8;";
  sleep 5;
  psql -U demogis -c "update incidencias set fk_estado = 3 where x_incidencia < 4;";
  psql -U demogis -c "update incidencias set fk_estado = 1 where x_incidencia < 8 and x_incidencia >= 4;";
  psql -U demogis -c "update incidencias set fk_estado = 2 where x_incidencia >= 8;";
  psql -U demogis -c "update incidencias set prioridad = prioridad + 2 where prioridad < 6";
  psql -U demogis -c "update incidencias set prioridad = prioridad - 2 where prioridad > 5";
  sleep 5;
done
