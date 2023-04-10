CREATE TABLE room (
    id_num serial primary key,
    id text,
    turn int,
    finished boolean,
    width int,
    height int,
    entries int[][],
    player_one_id text,
    player_two_id text,
    winner_id text,
    winning_chip_positions int[][]
);
