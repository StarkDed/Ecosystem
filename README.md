# Старков Денис  
  
  
## Экосистема  
Проект создает симуляцию экосистемы на основе введенных данных(данные нужно вводить в файлы).Симуляция создается засчет потоков,а именно **семафоров**(конретно в java реализуется через блок **synchronized**)  
  
## Основы программы  
Всего в прграмме взаимодейтвуют 3 вида сущности:  
- хищники  
- траводяные  
- растения  
### Растение (или herb)  
 Растения(или herbs) являются пищей для траводных.Из основных полей класса это поле **name**.  
### Животные (или animal)  
!Замечание:Все животные унаследованы от класса Animal,который имеет поля **name**,**timeStartHungry**(время начала голода) и **timeMillisecondsHungry**(время,сколько животное может прожить без еды)  
 **Травоядное**(или herbivore) поедают растения и являются пищей для хищников. Является потоком, поэтому реализует интерфейс Runnable. Из основных полей класса:  
- timeEatingFood(время поедания пиши)  
- herbs(коллекция раятений откуда траводное берет еду)  
Реализация метода run():  
Реализация блока synchronized:  
```
synchronized (herbs){
                while(herbs.isEmpty() && running){
                    try{
                        herbs.wait();
                    }catch(InterruptedException e){
                        // Дополнить обработку
                        return;
                    }
                }
```
Сверху поток вызывает метод wait() , если растения еще не появились .  
Дальше (если растения появились) травоядное животное находит рандомное растение из колекции и поедает его за определенное время  
**Хищник** (или predator)  
Поедают траводяных.Поля схожи с траводными и методом run  
## Возникновение сущностей  
Три класса ответсвенные за производство объектов:  
- KingdomOfHerbivores  
- KingdomOfHerbs  
- KingdomOfPredators  
Каждый из них является потоком и реализует метод run() в котором появляется новые потоки через промежуток времени.Когда появляется новый поток,определенная колекция вызывет метод notify() ,чтобы заблокированные потоки продолжили свою работу после метода wait)  
## Ввод данных или Работа с файлами  
В папке fileInput находятся файлы .txt (именем классов ) в которых вводятся данные.  
Первая строка задает время в мс через которое появится новая сущность.  
Остальные строки задают поля объектов.Для KingdomOfHerbivores.txt и KingdomOfPredators.txt свойства одинаковые(имя,уровеньГолода,время еды).  
Для KingdomOfHerbs.txt только имена.  
В классе Main происходит чтение файлов , а в классе Ecosystem запускаются потоки.  
## !!!Выход из програмыы  
Для выхода из программы нужно ввести '0' во время выполнения программы  
## Недостатки программы  
1. Зависит от мощности процессора жизнь животных(как быстро обработает блок syncronized)  
2. Уведомление о смерти по причине голода травоядного животного пройдет только после того как появится новое растение  
3. Первый поток чаще всего будет заходить в syncronized блок так как он первым запускается (сильный травоядный вытеснил более слабых от еды :))  
4. Ограниченное количество животных( long id)  
5. timeEating лучше было присвоить животному которого едят, а не тому кто ест.  
6. После завершения программы нужно подождать пару секунд,чтобы закрыть все потоки(время зависит от количества)  
