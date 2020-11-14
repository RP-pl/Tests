import tensorflow
from tensorflow import keras
from tensorflow.keras.utils import get_custom_objects
import numpy as np

def gelu(x):
    return 0.5*x*(1+keras.activations.tanh(((2/3.14)**0.5)*(x + 0.044715*x**3)))

def softplus(x):
    return tensorflow.math.log(1+tensorflow.math.exp(x))

get_custom_objects().update({'gelu':keras.layers.Activation(gelu)})
get_custom_objects().update({'custom_softplus':keras.layers.Activation(softplus)})
model = keras.Sequential([
    keras.layers.Reshape([28,28,1]),
    keras.layers.Conv2D(filters=512,activation=tensorflow.nn.elu,padding='same',kernel_size=[17,17]),
    keras.layers.Conv2D(filters=256,activation=tensorflow.nn.elu,padding='same',kernel_size=[11,11]),
    keras.layers.Conv2D(filters=128, activation=tensorflow.nn.elu, padding='same', kernel_size=[7, 7]),
    keras.layers.Conv2D(filters=64, activation=tensorflow.nn.elu, padding='same', kernel_size=[5, 5]),
    keras.layers.Flatten(),
    keras.layers.Dense(64,activation='gelu'),
    keras.layers.Dense(10,activation='custom_softplus')
])
(x_train, y_train), (x_test, y_test) = keras.datasets.mnist.load_data()
x_train = x_train.astype(np.float32) / 255
x_test = x_test.astype(np.float32) / 255
model.compile(optimizer=keras.optimizers.Nadam(learning_rate=1e-5),loss=keras.losses.sparse_categorical_crossentropy,metrics=['mse','accuracy'])
history = model.fit(x_train,y_train)