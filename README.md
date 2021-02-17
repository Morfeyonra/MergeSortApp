## MergeSortApp
 
### Особенности реализации
- Если не указано обратное, то реализация строго в соответствии с тз.
- Аргументы командной строки (-a, -d), (-s, -i) могут быть введены в обратном порядке.
- Имя выходного файла должно быть приемлемым для операционной и файловой систем.
- Программа может обрабатывать файлы с целыми числами, значения которых не помещаются в 64 байта.
- Программа может обрабатывать файлы, объем которых превышает объем оперативной памяти устройства, но требует наличия дополнительного места на жестком диске.
- Программа не способна обработать исходные файлы, _отдельные элементы_ которых превышают размер оперативной памяти устройства.

### Структура программы
- `public class Main`
    - `public static void main(String[] args)` - точка входа
    - `private static boolean doLogic(Arguments cmdArgs)` - метод внутри которого реализован процесс сортировки файлов
- `class Arguments` 
    - экземпляр данного класса хранит все корректные аргументы командной строки
    - `static List<String> parseCMDArgs(String[] args)` - проверка исходных аргументов командной строки
- `class MethodSetter` 
    - `public void setMethod(Arguments cmdArgs)` - делегирует выполнение нижеперечисленных методов, экземпляру класса конкретной сортировки через свой интерфейс
    - `public List<String> checkFiles(Arguments cmdArgs)`
    - `public File sortFiles(File file1, File file2, Arguments cmdArgs)`
- `interface WorkMethod`
    - `List<String> checkFiles(Arguments cmdArgs)`
    - `File sortFiles(File file1, File file2, Arguments cmdArgs)`
    - `default void collectGarbage(File file1, File file2, Arguments cmdArgs)`
    - интерфейс имплементируют следующие классы:
    - `class ASMergeSort implements WorkMethod` - вариант проверки и сортировки -a, -s
    - `class AIMergeSort implements WorkMethod` - вариант проверки и сортировки -a, -i
    - `class DSMergeSort implements WorkMethod` - вариант проверки и сортировки -d, -s
    - `class DIMergeSort implements WorkMethod` - вариант проверки и сортировки -d, -i
