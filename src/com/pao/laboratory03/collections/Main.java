package com.pao.laboratory03.collections;

import java.util.*;

/**
 * Exercițiul 1 — Colecții: HashMap și TreeMap
 *
 * Creează în acest main:
 *
 * PARTEA A — HashMap (frecvența cuvintelor)
 * 1. Declară un array de String-uri:
 *    String[] words = {"java", "python", "java", "c++", "python", "java", "rust", "c++", "go"};
 * 2. Creează un HashMap<String, Integer> care contorizează de câte ori apare fiecare cuvânt.
 *    - Parcurge array-ul și folosește put() + getOrDefault() pentru a incrementa contorul.
 * 3. Afișează map-ul.
 * 4. Verifică dacă există cheia "rust" cu containsKey().
 * 5. Afișează DOAR cheile (keySet()), apoi DOAR valorile (values()).
 * 6. Parcurge map-ul cu entrySet() și afișează "cheia -> valoarea" pentru fiecare intrare.
 *
 * PARTEA B — TreeMap (sortare automată)
 * 7. Creează un TreeMap<String, Integer> din același HashMap (constructor cu argument).
 * 8. Afișează TreeMap-ul — observă ordinea alfabetică a cheilor.
 * 9. Folosește firstKey() și lastKey() pentru a afișa prima și ultima cheie.
 *
 * PARTEA C — Map cu obiecte
 * 10. Creează un HashMap<String, List<String>> care asociază materii cu liste de studenți.
 *     Exemplu: "PAOJ" -> ["Ana", "Mihai", "Ion"], "BD" -> ["Ana", "Elena"]
 * 11. Afișează toți studenții de la materia "PAOJ".
 * 12. Adaugă un student nou la "BD" și afișează lista actualizată.
 *
 * Output așteptat (orientativ — ordinea HashMap poate varia):
 *
 * === PARTEA A: HashMap — frecvența cuvintelor ===
 * Frecvență: {python=2, c++=2, java=3, rust=1, go=1}
 * Conține 'rust'? true
 * Chei: [python, c++, java, rust, go]
 * Valori: [2, 2, 3, 1, 1]
 * python -> 2
 * c++ -> 2
 * java -> 3
 * rust -> 1
 * go -> 1
 *
 * === PARTEA B: TreeMap — sortare automată ===
 * Sortat: {c++=2, go=1, java=3, python=2, rust=1}
 * Prima cheie: c++
 * Ultima cheie: rust
 *
 * === PARTEA C: Map cu obiecte ===
 * Studenți la PAOJ: [Ana, Mihai, Ion]
 * Studenți la BD (actualizat): [Ana, Elena, George]
 */
public class Main {
    public static void main(String[] args) {
        String[] words = {"java", "python", "java", "c++", "python", "java", "rust", "c++", "go"};
        HashMap<String, Integer> cuvinte = new HashMap<String, Integer>();
        for(String cuvant : words)
        {
            Integer valoare = cuvinte.getOrDefault(cuvant, 0);
            valoare += 1;
            cuvinte.put(cuvant, valoare);
        }
        System.out.println(cuvinte);
        System.out.println(cuvinte.containsKey("rust"));
        System.out.println(cuvinte.keySet());
        System.out.println(cuvinte.values());
        for(Map.Entry<String, Integer> entry: cuvinte.entrySet())
        {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }


        TreeMap<String, Integer> cuvinteB = new TreeMap<String, Integer>(cuvinte);
        System.out.println(cuvinteB);
        System.out.println(cuvinteB.firstKey() + ' ' + cuvinteB.lastKey());


        HashMap<String, List<String>> materii = new HashMap<String, List<String>>();
        List<String> studenti = new ArrayList<String>(List.of("Ana", "Mihai", "Ion"));
        materii.put("PAOJ", studenti);
        List<String> studenti2 = new ArrayList<String>(List.of("Ana", "Elena", "George"));
        materii.put("BD", studenti2);

        System.out.println(materii.get("PAOJ"));
        List<String> lista = materii.getOrDefault("BD", Collections.emptyList());
        lista.add("NOU");
        materii.put("BD", lista);
        System.out.println(materii.get("BD"));

    }
}

