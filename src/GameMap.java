import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class GameMap {

    public int width, height;
    private int[][] rooms;
    private ArrayList<Room> roomList;

    public Room firstRoom;

    public GameMap(int maxWidth, int maxHeight) {

        width = maxWidth;
        height = maxHeight;
        rooms = new int[width][height];
        roomList = new ArrayList<>();
        firstRoom = new Room(new Random().nextInt(width), new Random().nextInt(height));
        rooms[firstRoom.x][firstRoom.y] = 2;
        roomList.add(firstRoom);
    }

    public void GenerateRoomsAround(Room room) {

        int[] vertical = { room.y - 1, room.y + 1 };
        int[] horizontal = { room.x - 1, room.x + 1 };

        for (int i = 0; i < 2; i++) {

            if (vertical[i] >= 0 && vertical[i] < height) {

                if (rooms[room.x][vertical[i]] == 0) {
                    rooms[room.x][vertical[i]] = new Random().nextInt(4) + 1;
                }
                if (rooms[room.x][vertical[i]] > 1)
                    AddRoom(new Room(room.x, vertical[i]));
            }
        }

        for (int i = 0; i < 2; i++) {

            if (horizontal[i] >= 0 && horizontal[i] < width) {

                if (rooms[horizontal[i]][room.y] == 0) {
                    rooms[horizontal[i]][room.y] = new Random().nextInt(4) + 1;
                }
                if (rooms[horizontal[i]][room.y] > 1)
                    AddRoom(new Room(horizontal[i], room.y));
            }
        }
    }

    void AddRoom(Room room) {

        boolean exists = false;

        for (int i = 0; i < roomList.size(); i++) {

            Room e = roomList.get(i);
            if (e.x == room.x && e.y == room.y)
                exists = true;
        }

        if (!exists)
            roomList.add(room);
    }

    public void Tick() {

        for (int i = 0; i < roomList.size(); i++) {

            Room e = roomList.get(i);

            e.Tick();
        }
    }

    public void Render(Graphics g) {

        g.setColor(Color.blue);
        g.drawRect(0 - Camera.x, 0 - Camera.y, width * 100, height * 100);

        for (int i = 0; i < roomList.size(); i++) {

            roomList.get(i).Render(g);
        }
    }
}