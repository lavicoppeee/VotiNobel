package it.polito.tdp.nobel.model;

import java.util.*;


import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	
	private List<Esame> esami;
	
	private double bestMedia=0.0;
	private Set<Esame> bestSoluzione=null;
	
	
	public Model() {
		EsameDAO dao=new EsameDAO();
		this.esami= dao.getTuttiEsami();
	}
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		
		Set<Esame> parziale=new HashSet<>();
		
		cerca2(parziale,0,numeroCrediti);
		
		
		return bestSoluzione;
	}
	
	/*APPROCCIO 1 */
	private void cerca1(Set<Esame> parziale, int l, int m) {
		//genero sotto problemi
		int crediti=sommaCrediti(parziale);
		
		//casi terminali
		if(crediti>m)
			return; 
	
		if(crediti==m) {
			double media=calcolaMedia(parziale);
			if(media > bestMedia) {
				//tengo traccia della miglior soluzione 
				bestSoluzione= new HashSet<>(parziale); 
				bestMedia=media; 
			}
		}
		
		//sicuramente crediti<m
		
		if(l==esami.size()) {
			return ; 
		}
		
		
		//aggiungo o no l'esame di l?
		
		//aggiungo l'esame
		parziale.add(esami.get(l));
		cerca1(parziale, l+1, m);
		parziale.remove(esami.get(l));
		
		//provo a non aggiungere l'esame 
		cerca1(parziale, l+1, m);
		
		
		
	}
	
	/*APPROCCIO 2 */
	
	private void cerca2(Set<Esame> parziale, int l, int m) {
		// genero sotto problemi
		int crediti = sommaCrediti(parziale);

		// casi terminali
		if (crediti > m)
			return;

		if (crediti == m) {
			double media = calcolaMedia(parziale);
			if (media > bestMedia) {
				// tengo traccia della miglior soluzione
				bestSoluzione = new HashSet<>(parziale);
				bestMedia = media;
			}
		}

		// sicuramente crediti<m

		if (l == esami.size()) {
			return;
		}

		// generiamo i sottoproblemi
		for (Esame e : esami) {
			if (!parziale.contains(e)) {
				parziale.add(e);
				cerca2(parziale, l + 1, m);
				parziale.remove(e);
			}

		}
		
		
		
	}
	
	
	
	public double calcolaMedia(Set<Esame> parziale) {
		// TODO Auto-generated method stub
		int crediti=0;
		int somma=0;
		
		for(Esame e: parziale) {
			crediti+= e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
			
	
		return somma /crediti;
	}
	private int sommaCrediti(Set<Esame> parziale) {
		// TODO Auto-generated method stub
		int somma=0;
		for(Esame e: parziale)
			somma+= e.getCrediti();
		
		return somma;
	}

}

