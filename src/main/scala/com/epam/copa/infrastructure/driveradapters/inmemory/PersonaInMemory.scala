package com.epam.copa
package com.epam.copa.infrastructure.driveradapters.inmemory
import com.epam.copa.infrastructure.driveradapters.objects.Persona

object PersonaInMemory {
  
  private val data: List[Persona] = List(
    new Persona("1",  "CC",  "1010101010", "Camilo",  "Ruiz",   "camilo@mail.com",  "3000000001"),
    new Persona("2",  "TI",  "2020202020", "Laura",   "Gomez",  "laura@mail.com",   "3000000002"),
    new Persona("3",  "RC",  "3030303030", "Juan",    "Lopez",  "juan@mail.com",    "3000000003"),
    new Persona("4",  "CE",  "4040404040", "Maria",   "Diaz",   "maria@mail.com",   "3000000004"),
    new Persona("5",  "PA",  "AA1234567",  "Pedro",   "Perez",  "pedro@mail.com",   "3000000005"),
    new Persona("6",  "NIT", "900123456",  "Empresa", "SAS",    "empresa@mail.com", "3000000006"),
    new Persona("7",  "PEP", "PEP-11111",  "Ana",     "Rios",   "ana@mail.com",     "3000000007"),
    new Persona("8",  "PPT", "PPT-22222",  "Sofia",   "Luna",   "sofia@mail.com",   "3000000008"),
    new Persona("9",  "CC",  "5050505050", "Andres",  "Mora",   "andres@mail.com",  "3000000009"),
    new Persona("10", "CC",  "6060606060", "Julian",  "Vega",   "julian@mail.com",  "3000000010"),

    new Persona("11", "CC",  "7070707070", "Paula",   "Silva",  "paula@mail.com",   "3000000011"),
    new Persona("12", "TI",  "8080808080", "Nicolas", "Rojas",  "nico@mail.com",    "3000000012"),
    new Persona("13", "CE",  "9090909090", "Diana",   "Gil",    "diana@mail.com",   "3000000013"),
    new Persona("14", "PA",  "BB7654321",  "Felipe",  "Castro", "felipe@mail.com",  "3000000014"),
    new Persona("15", "NIT", "800987654",  "Tech",    "LTDA",   "tech@mail.com",    "3000000015"),
    new Persona("16", "PEP", "PEP-33333",  "Sara",    "Mejia",  "sara@mail.com",    "3000000016"),
    new Persona("17", "PPT", "PPT-44444",  "Kevin",   "Ortiz",  "kevin@mail.com",   "3000000017"),
    new Persona("18", "RC",  "1111222233", "Valeria", "Nunez",  "vale@mail.com",    "3000000018"),
    new Persona("19", "CC",  "2222333344", "Daniel",  "Rey",    "daniel@mail.com",  "3000000019"),
    new Persona("20", "OTRO","OT-99999",   "Alex",    "Torres", "alex@mail.com",    "3000000020")
  )
  
  def findAll(): List[Persona] = data


  def findById(id: String): Option[Persona] =
    data.find(_.getId == id)
  

}
