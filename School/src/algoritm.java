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

            System.out.println(part.value +
                    " pos: " +
                    part.position[0] +
                    " " +
                    part.position[1] +
                    " - " +
                    part.is_mobile(field, (byte) 0) +
                    " : final " +
                    part.get_final_position()[0] +
                    " " +
                    part.get_final_position()[1] +
                    "\n" +
                    " next: " + part.get_next(field).value);
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
        double distance = Part.get_distance(final_pos, part.position);

        return true;
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
