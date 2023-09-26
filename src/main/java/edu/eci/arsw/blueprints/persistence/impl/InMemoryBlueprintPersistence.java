/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{
    //Mapeo de persistencia
    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts0=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp0=new Blueprint("Sebas", "Blueprint 1",pts0);
        Point[] pts1=new Point[]{new Point(125, 125),new Point(110, 110),new Point(0, 0)};
        Blueprint bp1=new Blueprint("Sebas", "Blueprint 2",pts0);
        Point[] pts2=new Point[]{new Point(170, 110),new Point(135, 150)};
        Blueprint bp2=new Blueprint("STian", "Blueprint 3",pts0);
        blueprints.put(new Tuple<>(bp0.getAuthor(),bp0.getName()), bp0);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bluePrints = new HashSet<>();
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (author.equals(entry.getKey().getElem1())) {
                bluePrints.add(entry.getValue());
            }
        }
        if (bluePrints.isEmpty()) {
            throw new BlueprintNotFoundException("Blueprints not found for author: " + author);
        }
        return bluePrints;
    }
    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> bluePrints = new HashSet<>();
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            bluePrints.add(entry.getValue());
        }
        if (bluePrints.isEmpty()) {
            throw new BlueprintNotFoundException("Blueprints is empty");
        }
        return bluePrints;
    }

    @Override
    public void setBlueprint(String author, String bpname, Blueprint nbp) throws BlueprintNotFoundException {
        Blueprint bp = getBlueprint(author, bpname);
        bp.setPoints(nbp.getPoints());
    }


}
