import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class algoritm
{
    public static void main(String []args)
    {
        Date d1 = new Date();
        for (int i = 0; i < 1; i++)
        {
            ArrayList<ArrayList<Part>>  field = new ArrayList<>();
            List<Integer>               list_value = new ArrayList<>();

            int size = 3;
            fill_field_random(field, list_value, size);
            int []position = Part.get_position(field, 0);
            Part part = field.get((int) (Math.random() * size)).get((int) (Math.random() * size));

            System.out.println(part.value + " pos: " + part.position[0] + " " + part.position[1] + " - " + part.is_mobile(field, (byte) 0) + " : final " + part.get_final_position()[0] + " " + part.get_final_position()[1] + "\n"
                    + " next: " + part.get_next(field).value);
            print_field(field);
        }
        Date d2 = new Date();
        System.out.println(d2.getTime() - d1.getTime());
    }

    public static boolean algos_part_1(ArrayList<ArrayList<Part>>  field, int value)
    {
        if (Part.get_part(field, value).block)
        {
            if ((Part.size * (Part.size - 2)) == value)
                return true;
            else
                algos_part_1(field, value + 1);
        }
        return true;
    }

    public static boolean part_to_final(ArrayList<ArrayList<Part>>  field, int value)
    {
        Part part = Part.get_part(field, value);

        int[]   final_pos = part.get_final_position();
        double  distance = Part.get_distance(final_pos, part.position);

        return true;
    }

    public static class Part
    {
        static int  size;
        int         value;
        int[]       position;
        boolean     block;
        boolean     move;

        public  Part(int value, int[] position)
        {
            this.move = false;
            this.block = false;
            this.value = value;
            this.position = position;
        }

        public int[] get_final_position()
        {
            if (value == 0)
                return new int[]{size - 1, size - 1};
            return new int[]{(value - 1) / size, (value - 1) % size};
        }

        public static Part get_part(ArrayList<ArrayList<Part>> field, int value)
        {
            int []position = get_position(field, value);
            if (position == null)
                return null;
            return field.get(position[0]).get(position[1]);
        }

        public static Part get_part(ArrayList<ArrayList<Part>> field, int[] position)
        {
            if (position[0] < 0 || position[0] > Part.size - 1 || position[1] < 0 || position[1] > Part.size - 1)
                return null;
            return field.get(position[0]).get(position[1]);
        }

        public Part get_next(ArrayList<ArrayList<Part>>  field)
        {
            int[]   final_pos = get_final_position();
            double  distance = Part.get_distance(final_pos, position);
            double  dis_min = -1;
            int[]   pos_next = null;
            Part    part_next = null;

            if (distance == 0)
                return this;
            for (int i = 0; i < 4; i++)
            {
                if (i == 0)
                    part_next = Part.get_part(field, new int[]{position[0] - 1, position[1]});
                if (i == 1)
                    part_next = Part.get_part(field, new int[]{position[0], position[1] + 1});
                if (i == 2)
                    part_next = Part.get_part(field, new int[]{position[0] + 1, position[1]});
                if (i == 3)
                    part_next = Part.get_part(field, new int[]{position[0], position[1] - 1});
                if (part_next == null || part_next.move || part_next.block)
                    continue;
                if (dis_min == -1)
                {
                    dis_min = Part.get_distance(part_next.position, final_pos);
                    pos_next = part_next.position;
                }
                else if (Part.get_distance(part_next.position, final_pos) < dis_min)
                {
                    dis_min = Part.get_distance(part_next.position, final_pos);
                    pos_next = part_next.position;
                }
            }
            return Part.get_part(field, pos_next);
        }

        public static double get_distance(int[] pos1, int[] pos2)
        {
            int a = Math.abs(pos1[0] - pos2[0]);
            int b = Math.abs(pos1[1] - pos2[1]);

            return Math.sqrt(a * a + b * b);
        }

        public static int[] get_position(ArrayList<ArrayList<Part>> field, int value)
        {
            for (int i = 0; i < field.size(); i++)
            {
                for (int j = 0; j < field.get(i).size(); j++)
                {
                    if (field.get(i).get(j).value == value)
                        return field.get(i).get(j).position;
                }
            }
            return null;
        }

        public static void set_size(int size)
        {
            Part.size = size;
        }

        public boolean is_empty()
        {
            if (value == 0)
                return true;
            return false;
        }

        public boolean is_mobile(ArrayList<ArrayList<Part>> field, byte dir)
        {
            if (dir == 0)
            {
                if (position[0] == 0)
                    return false;
                if (get_position(field, 0)[1] == position[1] &&
                        get_position(field, 0)[0] == position[0] - 1)
                    return true;
                return false;
            }
            if (dir == 1)
            {
                if (position[1] == size)
                    return false;
                if (get_position(field, 0)[1] == position[1] &&
                        get_position(field, 0)[0] == position[0] - 1)
                    return true;
                return false;
            }
            return false;
        }
    }

    public static void fill_field_random(ArrayList<ArrayList<Part>> field, List<Integer> list_value, int size)
    {
        Part.set_size(size);

        for (int i = 0; i < size; i++)
        {
            field.add(new ArrayList<>());
            for (int i1 = 0; i1 < size; i1++)
            {
                field.get(i).add(new Part(get_rand_value(list_value, size), new int[]{i, i1}));
            }
        }
    }

    public static int get_rand_value(List<Integer> list_value, int size)
    {
        int ret;

        while (true)
        {
            ret = (int) (Math.random() * (size * size));
            if (!list_value.contains(ret))
            {
                list_value.add(ret);
                return ret;
            }
        }
    }

    public static void print_field(ArrayList<ArrayList<Part>> field)
    {
        String field_str = "";

        for (ArrayList<Part> part_list : field)
        {
            if (field_str.split("").length != 0)
                field_str += "\n";
            for (Part part : part_list)
            {
                field_str += (part.value < 10 ? (" " + part.value) : part.value) + " ";
            }
        }
        field_str += "\n";

        System.out.print(field_str);
    }
}
