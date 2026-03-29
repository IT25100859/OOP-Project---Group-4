package com.components3.Movie.ticket.Reservation.Platform.repository;

import com.components3.Movie.ticket.Reservation.Platform.bean.ShowTime;
import com.components3.Movie.ticket.Reservation.Platform.bean.TheaterHall;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShowTimeRepository {
    private static final String Showtimes_file = "IT25100859/data/showtimes.txt";
    private static final String halls_file = "IT25100859/data/halls.txt";



    public List<ShowTime> readAll(){

        List<ShowTime> list = new ArrayList<>();
        File file = new File(Showtimes_file);

        if(!file.exists()){
            return list;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while((line = reader.readLine()) != null){
                line = line.trim();
                if(!line.isEmpty()){
                    ShowTime s = ShowTime.fromFileString(line);
                    if(s!=null) list.add(s);
                }

            }
        }catch(IOException e){
            System.out.println("Error reading showtimes.txt: "+e.getMessage());

        }
        return list;
    }


    public void WriteAll(List<ShowTime> showTimes){
        new File("IT25100859/data").mkdirs();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Showtimes_file,false))){
            for(ShowTime s: showTimes){
                writer.write(s.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to showtimes.txt: "+e.getMessage());
        }
    }

    public ShowTime findbyID(String showtimeID){

        return readAll().stream()
                .filter(s->s.getShowTimeID().equals(showtimeID))
                .findFirst()
                .orElse(null);
    }

    public void save(ShowTime showTime){
        List<ShowTime> all = readAll();
        all.add(showTime);
        WriteAll(all);

    }

    public boolean Update(ShowTime updated){
        List<ShowTime> all = readAll();
        boolean found = false;

        for(int i=0; i< all.size(); i++){
            if(all.get(i).getShowTimeID().equals(updated.getShowTimeID())){
                all.set(i,updated);
                found = true;
                break;
            }
        }
        if(found){
            WriteAll(all);
        }
        return found;
    }

    public boolean delete(String showtimeID){
        List<ShowTime> all = readAll();
        boolean removed = all.removeIf(s->s.getShowTimeID().equals(showtimeID));
        if(removed){
            WriteAll(all);
        }
        return removed;
    }

    public List<ShowTime> findByDate(String date){
        List<ShowTime> result = new ArrayList<>();

        for(ShowTime s: readAll()){
            if(s.getShowDate().equals(date)){
                result.add(s);
            }
        }
        return result;
    }

    public List<ShowTime> findByHall(String hallID){
        List<ShowTime> result = new ArrayList<>();

        for(ShowTime s: readAll()){
            if(s.getHallID().equals(hallID)){
                result.add(s);
            }
        }
        return result;
    }

    public String generateNextID(){
        List<ShowTime> all = readAll();
        int max = 0;
        for(ShowTime s: all) {
            try {
                int num = Integer.parseInt(s.getShowTimeID().replace("ST",""));
                if(num>max){
                    max = num;
                }
            }catch (NumberFormatException ignored){}

        }
        return String.format("ST%03d", max + 1);
    }

    // Operations on Hall File


    public List<TheaterHall> readAllHalls() {
        List<TheaterHall> halls = new ArrayList<>();
        File file = new File(halls_file);

        if (!file.exists()) {

            return getDefaultHalls();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    halls.add(TheaterHall.fromFileString(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading halls.txt: " + e.getMessage());
            return getDefaultHalls();
        }
        return halls;
    }

    private List<TheaterHall> getDefaultHalls() {
        List<TheaterHall> defaults = new ArrayList<>();
        defaults.add(new TheaterHall("H001", "Hall A",  8, 10, "STANDARD_2D"));
        defaults.add(new TheaterHall("H002", "Hall B",  8, 10, "PREMIUM_3D"));
        defaults.add(new TheaterHall("H003", "IMAX Hall", 10, 15, "IMAX"));

        writeAllHalls(defaults);
        return defaults;
    }


    public void writeAllHalls(List<TheaterHall> halls) {
        new File("data").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(halls_file, false))) {
            for (TheaterHall h : halls) {
                writer.write(h.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing halls.txt: " + e.getMessage());
        }
    }

    public TheaterHall findHallById(String hallId) {
        return readAllHalls().stream()
                .filter(h -> h.getHallID().equals(hallId))
                .findFirst()
                .orElse(null);
    }














}
