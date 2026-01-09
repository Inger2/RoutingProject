package routing.dto;


import java.util.List;

public record TwoGisResponse (List<Result> result)  {
    public record Result(int total_duration){

    }

}
